package io.surfkit.clientlib.pdf
/*
https://github.com/mozilla/pdf.js/blob/master/src/display/api.js
 */

import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation._
import scala.scalajs.js.typedarray.Uint8Array

package object pdflib extends js.GlobalScope {
  var PDFJS: PDFJSStatic = js.native
}

@js.native
trait PDFTreeNode extends js.Object {
  var title: String = js.native
  var bold: Boolean = js.native
  var italic: Boolean = js.native
  var color: js.Array[Double] = js.native
  var dest: js.Any = js.native
  var items: js.Array[PDFTreeNode] = js.native
}

@js.native
trait PDFInfo extends js.Object {
  var PDFFormatVersion: String = js.native
  var IsAcroFormPresent: Boolean = js.native
  var IsXFAPresent: Boolean = js.native
  @JSBracketAccess
  def apply(key: String): js.Any = js.native
  @JSBracketAccess
  def update(key: String, v: js.Any): Unit = js.native
}

@js.native
trait PDFMetadata extends js.Object {
  def parse(): Unit = js.native
  def get(name: String): String = js.native
  def has(name: String): Boolean = js.native
}

@js.native
trait PDFSource extends js.Object {
  var url: String = js.native
  var data: Uint8Array = js.native
  var httpHeaders: js.Any = js.native
  var password: String = js.native
}

@js.native
trait PDFProgressData extends js.Object {
  var loaded: Double = js.native
  var total: Double = js.native
}

@js.native
trait PDFDocumentProxy extends js.Object {
  var numPages: Double = js.native
  var fingerprint: String = js.native
  def embeddedFontsUsed(): Boolean = js.native
  def getPage(number: Double): Promise[PDFPageProxy] = js.native
  def getDestinations(): Promise[js.Array[js.Any]] = js.native
  def getJavaScript(): Promise[js.Array[String]] = js.native
  def getOutline(): Promise[js.Array[PDFTreeNode]] = js.native
  def getMetadata(): Promise[js.Any] = js.native
  def isEncrypted(): Promise[Boolean] = js.native
  def getData(): Promise[Uint8Array] = js.native
  def dataLoaded(): Promise[js.Array[js.Any]] = js.native
  def destroy(): Unit = js.native
}

@js.native
trait PDFRef extends js.Object {
  var num: Double = js.native
  var gen: js.Any = js.native
}

@js.native
trait PDFPageViewportOptions extends js.Object {
  var viewBox: js.Any = js.native
  var scale: Double = js.native
  var rotation: Double = js.native
  var offsetX: Double = js.native
  var offsetY: Double = js.native
  var dontFlip: Boolean = js.native
}

@js.native
trait PDFPageViewport extends js.Object {
  var width: Double = js.native
  var height: Double = js.native
  var fontScale: Double = js.native
  var transforms: js.Array[Double] = js.native
  def clone(options: PDFPageViewportOptions): PDFPageViewport = js.native
  def convertToViewportPoint(): js.Array[Double] = js.native
  def convertToViewportRectangle(): js.Array[Double] = js.native
  def convertToPdfPoint(): js.Array[Double] = js.native
}

@js.native
trait PDFAnnotationData extends js.Object {
  var subtype: String = js.native
  var rect: js.Array[Double] = js.native
  var annotationFlags: js.Any = js.native
  var color: js.Array[Double] = js.native
  var borderWidth: Double = js.native
  var hasAppearance: Boolean = js.native
}

@js.native
trait PDFAnnotations extends js.Object {
  def getData(): PDFAnnotationData = js.native
  def hasHtml(): Boolean = js.native
  def getHtmlElement(commonOjbs: js.Any): HTMLElement = js.native
  def getEmptyContainer(tagName: String, rect: js.Array[Double]): HTMLElement = js.native
  def isViewable(): Boolean = js.native
  def loadResources(keys: js.Any): Promise[js.Any] = js.native
  def getOperatorList(evaluator: js.Any): Promise[js.Any] = js.native
}

@js.native
trait PDFRenderTextLayer extends js.Object {
  def beginLayout(): Unit = js.native
  def endLayout(): Unit = js.native
  def appendText(): Unit = js.native
}

@js.native
trait PDFRenderImageLayer extends js.Object {
  def beginLayout(): Unit = js.native
  def endLayout(): Unit = js.native
  def appendImage(): Unit = js.native
}

@js.native
trait PDFRenderParams extends js.Object {
  var canvasContext: CanvasRenderingContext2D = js.native
  var textLayer: PDFRenderTextLayer = js.native
  var imageLayer: PDFRenderImageLayer = js.native
  var continueCallback: js.Function1[js.Function0[Unit], Unit] = js.native
}

object PDFRenderParams {
  def apply(
             canvasContext:  js.UndefOr[CanvasRenderingContext2D] = js.undefined,
             textLayer: js.UndefOr[PDFRenderTextLayer] = js.undefined,
             viewport: js.UndefOr[PDFPageViewport] = js.undefined,
             imageLayer: js.UndefOr[PDFRenderImageLayer] = js.undefined,
             continueCallback: js.UndefOr[js.Function1[js.Function0[Unit], Unit]] = js.undefined
           ): PDFRenderParams = {
    val result = js.Dynamic.literal()
    canvasContext.foreach(result.canvasContext = _)
    textLayer.foreach(result.textLayer = _)
    viewport.foreach(result.viewport = _)
    imageLayer.foreach(result.imageLayer = _)
    continueCallback.foreach(result.continueCallback = _)
    result.asInstanceOf[PDFRenderParams]
  }
}

@js.native
trait PDFViewerParams extends js.Object {
  var container: HTMLElement = js.native
  var viewer: HTMLElement = js.native
}

object PDFViewerParams {
  def apply(
             container:  js.UndefOr[HTMLElement] = js.undefined,
             viewer: js.UndefOr[HTMLElement] = js.undefined
           ): PDFViewerParams = {
    val result = js.Dynamic.literal()
    container.foreach(result.container = _)
    viewer.foreach(result.viewer = _)
    result.asInstanceOf[PDFViewerParams]
  }
}

@js.native
trait PDFRenderTask extends Promise[PDFPageProxy] {
  def cancel(): Unit = js.native
}

@js.native
trait PDFPageProxy extends js.Object {
  def pageNumber(): Double = js.native
  def rotate(): Double = js.native
  def ref(): PDFRef = js.native
  def view(): js.Array[Double] = js.native
  def getViewport(scale: Double, rotate: Double = ???): PDFPageViewport = js.native
  def getAnnotations(): Promise[PDFAnnotations] = js.native
  def render(params: PDFRenderParams): PDFRenderTask = js.native
  def getTextContent(): Promise[TextContent] = js.native
  def destroy(): Unit = js.native
}

@js.native
trait TextContentItem extends js.Object {
  var str: String = js.native
  var transform: js.Array[Double] = js.native
  var width: Double = js.native
  var height: Double = js.native
  var dir: String = js.native
  var fontName: String = js.native
}

@js.native
trait TextContent extends js.Object {
  var items: js.Array[TextContentItem] = js.native
  var styles: js.Any = js.native
}

@js.native
trait PDFObjects extends js.Object {
  def get(objId: js.Any, callback: js.Any = ???): js.Dynamic = js.native
  def resolve(objId: js.Any, data: js.Any): js.Dynamic = js.native
  def isResolved(objId: js.Any): Boolean = js.native
  def hasData(objId: js.Any): Boolean = js.native
  def getData(objId: js.Any): js.Dynamic = js.native
  def clear(): Unit = js.native
}

@js.native
trait PDFJSStatic extends js.Object {
  var workerSrc: String = js.native
  var disableWorker: Boolean = js.native
  var maxImageSize: Double = js.native
  var disableFontFace: Boolean = js.native
  def getDocument(source: String,
                  pdfDataRangeTransport: js.Any = ???,
                  passwordCallback: js.Function2[js.Function1[String, Unit], String, String] = ???,
                  progressCallback: js.Function1[PDFProgressData, Unit] = ???): Promise[PDFDocumentProxy] = js.native
  def PDFViewer(params: PDFViewerParams): Unit = js.native
}
