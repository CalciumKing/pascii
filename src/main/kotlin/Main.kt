import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.javacv.OpenCVFrameGrabber
import org.bytedeco.opencv.opencv_core.Mat

fun main() {
	val grabber = OpenCVFrameGrabber(0)  // 0 = default camera
	val converter = OpenCVFrameConverter.ToMat()
	grabber.start()
	
	val canvas = CanvasFrame("pascii")
	
	while (canvas.isVisible) {
		val frame: Frame = grabber.grab()
		val mat: Mat = converter.convert(frame)
		
		canvas.showImage(frame)
	}
	
	grabber.stop()
	canvas.dispose()
}
