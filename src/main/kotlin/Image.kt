import org.bytedeco.opencv.opencv_core.Mat

data class Image(val pixels: List<List<Float>>) {
	
	constructor(frame: Mat) : this(frameToFloat(frame))
	
	fun printASCII() {
		val symbols: List<Char> = listOf('.', ',', '-', '~', ':', ';', '!', '#', '$', '@')
		for (floats: List<Float> in pixels) {
			for (v: Float in floats) {
				val index: Int = (v.toInt() * 10).coerceAtMost(9)
				print(symbols[index])
			}
			println()
		}
	}
}

private fun frameToFloat(frame: Mat): List<List<Float>> {
}
