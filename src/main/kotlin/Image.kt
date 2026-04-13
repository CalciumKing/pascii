import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.random.Random

data class Image(var pixelValues: List<List<Float>>, val settings: AsciiSettings) {
	
	constructor(frame: Frame, settings: AsciiSettings) : this(frameToFloat(frame, settings), settings){
		scaleDown(settings.resX, settings.resY)
		
		if (settings.isMirrored)
			flipHorizontal()
		if (settings.onlyEdges)
			detectEdges()
	}
	
	fun getAscii(): List<List<Char>> {
		val asciiImage: MutableList<MutableList<Char>> = MutableList(pixelValues.size) { MutableList(pixelValues[0].size) { ' ' } }
		val symbols: CharArray = settings.charSet.getChars()
		
		for ((i: Int, floats: List<Float>) in pixelValues.withIndex()) {
			for ((j: Int, v: Float) in floats.withIndex()) {
				val index: Int = (v * (symbols.size - 1)).toInt()
				asciiImage[i][j] = symbols[index]
			}
		}
		
		return asciiImage
	}
	
	fun flipHorizontal() {
		val height = pixelValues.size
		val width = pixelValues[0].size
		
		val flipped = MutableList(height) { MutableList(width) { 0f } }
		
		for (x in 0 until width)
			for (y in 0 until height)
				flipped[y][x] = pixelValues[y][width - 1 - x]
		
		pixelValues = flipped
	}
	
	fun scaleDown(targetWidth: Int, targetHeight: Int) {
		val sourceHeight: Int = pixelValues.size
		val sourceWidth: Int = pixelValues[0].size
		val newPixels: MutableList<MutableList<Float>> = MutableList(targetHeight) {
			MutableList(targetWidth) { 0f }
		}
		
		val xRatio: Float = sourceWidth.toFloat() / targetWidth
		val yRatio: Float = sourceHeight.toFloat() / targetHeight
		
		for (x in 0 until targetWidth) {
			for (y in 0 until targetHeight) {
				
				val startX = (x * xRatio).toInt()
				val startY = (y * yRatio).toInt()
				
				var sum = 0f
				var count = 0
				
				for (xi in 0 until xRatio.toInt().coerceAtLeast(1)) {
					for (yi in 0 until yRatio.toInt().coerceAtLeast(1)) {
						val px = (startX + xi).coerceAtMost(sourceWidth - 1)
						val py = (startY + yi).coerceAtMost(sourceHeight - 1)
						
						sum += pixelValues[py][px]
						count++
					}
				}
				
				newPixels[y][x] = sum / count
			}
		}
		
		pixelValues = newPixels
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
			val normalized: Float = grey / 255f  // normalize in 0-1 range, 1 - ... inverts colors
			
			floatArray[y][x] = normalized
		}
	}
	
	return floatArray
}
