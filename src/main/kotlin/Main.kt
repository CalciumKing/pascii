import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.OpenCVFrameGrabber

fun main() {
	val grabber = OpenCVFrameGrabber(0)  // 0 = default camera
	grabber.start()
	
	val window = AsciiWindow()
	
	while (true) {
		val frame: Frame = grabber.grab()
		val image = Image(frame, window.settings)
		
		val asciiString: String = buildString {
			for (row in image.getAscii()) {
				for (c in row)
					append(c)
				append("\n")
			}
		}
		
		window.setAsciiArea(asciiString)
	}
	
	grabber.stop()
}
