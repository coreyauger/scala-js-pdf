package demo

import io.surfkit.clientlib.pdf._
import org.scalajs.dom._
import scala.scalajs.js
import org.scalajs.dom
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.Thenable.Implicits._
import scala.concurrent.ExecutionContext.Implicits.global

object ScalaPdfExample extends js.JSApp {
  def main(): Unit = {
    // It is also possible to disable workers via `PDFJS.disableWorker = true`,
    // however that might degrade the UI performance in web browsers.
    pdflib.PDFJS.workerSrc = "/build/browserify/pdf.worker.bundle.js"
    for{
      pdfDocument <- pdflib.PDFJS.getDocument("../helloworld/pdf-test.pdf")
      pdfPage <- pdfDocument.getPage(1)
    }yield{
      var viewport = pdfPage.getViewport(1.0)
      var canvas = dom.document.getElementById("theCanvas2").asInstanceOf[dom.html.Canvas]
      canvas.width = viewport.width.toInt
      canvas.height = viewport.height.toInt
      var ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
      pdfPage.render(PDFRenderParams(
        canvasContext = ctx,
        viewport = viewport
      ))
    }
  }
}


/*

var pdfPath = '../helloworld/helloworld.pdf';

// Setting worker path to worker bundle.
PDFJS.workerSrc = '../../build/browserify/pdf.worker.bundle.js';

// It is also possible to disable workers via `PDFJS.disableWorker = true`,
// however that might degrade the UI performance in web browsers.

// Loading a document.
var loadingTask = PDFJS.getDocument(pdfPath);
loadingTask.promise.then(function (pdfDocument) {
  // Request a first page
  return pdfDocument.getPage(1).then(function (pdfPage) {
    // Display page on the existing canvas with 100% scale.
    var viewport = pdfPage.getViewport(1.0);
    var canvas = document.getElementById('theCanvas');
    canvas.width = viewport.width;
    canvas.height = viewport.height;
    var ctx = canvas.getContext('2d');
    var renderTask = pdfPage.render({
      canvasContext: ctx,
      viewport: viewport
    });
    return renderTask.promise;
  });
}).catch(function (reason) {
  console.error('Error: ' + reason);
});
 */
