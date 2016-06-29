package io.surfit.server

import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.{File, IOException}
import scala.concurrent.{ExecutionContextExecutor, Future}

trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

  lazy val ipApiConnectionFlow: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(config.getString("services.ip-api.host"), config.getInt("services.ip-api.port"))

  val routes = {
    logRequestResult("server") {
      get {
        pathSingleSlash {
          getFromFile("./demo/index-fastopt.html")
        } ~ path("js" / "pdf-demo-fastopt.js"){
          getFromFile("./demo/target/scala-2.11/pdf-demo-fastopt.js")
        } ~ path("build" / "browserify" / "main.bundle.js"){
          getFromFile("./demo/build/browserify/main.bundle.js")
        } ~ path("build" / "browserify" / "pdf.worker.bundle.js"){
          getFromFile("./demo/build/browserify/pdf.worker.bundle.js")
        } ~ path("helloworld" / "helloworld.pdf"){
          getFromFile("./demo/helloworld/helloworld.pdf")
        } ~ path("helloworld" / "brochure.pdf"){
           getFromFile(new File("./demo/helloworld/brochure.pdf"))
        }~ path("helloworld" / "pdf-test.pdf"){
          getFromFile(new File("./demo/helloworld/pdf-test.pdf"))
        }
      }
    }
  }
}

object Server extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
