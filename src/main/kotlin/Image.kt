import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage

data class Image(val pixelValues: List<List<Float>>) {
	
	constructor(frame: Frame) : this(frameToFloat(frame))
	
	fun getAscii(): List<List<Char>> {
		val symbols: List<Char> = listOf('.', ',', '-', '~', ':', ';', '!', '#', '$', '@')
//		val symbols: CharArray = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^'.`".reversed().toCharArray()
		val asciiImage: MutableList<MutableList<Char>> = MutableList(pixelValues.size) { MutableList(pixelValues[0].size) { ' ' } }
		
		for ((i: Int, floats: List<Float>) in pixelValues.withIndex()) {
			for ((j: Int, v: Float) in floats.withIndex()) {
				val index: Int = (v * (symbols.size - 1)).toInt()
				asciiImage[i][j] = symbols[index]
			}
		}
		
		return asciiImage
	}
}

private fun frameToFloat(frame: Frame): List<List<Float>> {
	val image: BufferedImage = Java2DFrameConverter().convert(frame)
	
	val height = image.height
	val width = image.width
	
	val floatArray: MutableList<MutableList<Float>> = MutableList(height) { MutableList(width) { 0f } }
	
	for (x in 0 until width) {
		for (y in 0 until height) {
			val pixel: Int = image.getRGB(x, y)
			val grey = (pixel shr 16) and 0xFF  // extract greyscale value using red component
			val normalized: Float = 1f - (grey / 255f)  // normalize in 0-1 range, 1 - ... inverts colors
			
			floatArray[y][x] = normalized
		}
	}
	
	return floatArray
}
