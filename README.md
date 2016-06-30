# scala-js-pdf
Type-safe and Scala-friendly library over pdf.js.


# Getting Started
There are 3 projects defined in the build.sbt:
* `root` the scala-js-pdf wrapper lib.
* `demo` a very simple scala-js example project
* `server` a quick and dirty akka http server for serving up your files (required for reading the pdf)

# Usage
To get the demo up and running you can clone the project into your home directory.  Next `cd scala-js-pdf` and fire up `sbt`.  Once you get to your sbt prompt you can change into the demo project and build it with the following commands:
```
project demo
fastOptJS
```
Next we will run the server with the following
```
project server
run
```
You can now direcct you browser at `http://localhost:9000` to view the pdf.  :)

# Demo Code
Here is a simple demo of how to write your own app.  Note that with the conversion from javascript Promise to Future we can get thie really nice for-comprehension syntax :)

```scala
import ...

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
```


