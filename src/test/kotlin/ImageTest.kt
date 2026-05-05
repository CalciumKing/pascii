import org.bytedeco.javacv.Java2DFrameConverter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import kotlin.test.assertContentEquals

class ImageTest {
	private lateinit var pixels: MutableList<MutableList<Float>>
	private lateinit var image: Image
	private lateinit var settings: AsciiSettings
	
	@BeforeEach
	fun setUp() {
		pixels = MutableList(10) { x ->
			MutableList(10) { y ->
				if (x == y) 1f else 0f
			}
		}
		
		settings = AsciiSettings(
			isMirrored = false,
			resX = pixels.size,
			resY = pixels[0].size
		)
		
		image = Image(pixels, settings)
	}
	
	@Test
	fun frameToFloat() {
		val width = 10
		val height = 10
		
		val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		
		for (y in 0 until height) {
			for (x in 0 until width) {
				val value = if (x == y) 255 else 0
				val rgb = (value shl 16) or (value shl 8) or value
				bufferedImage.setRGB(x, y, rgb)
			}
		}
		
		val converter = Java2DFrameConverter()
		val frame = converter.convert(bufferedImage)
		
		val result: List<List<Float>> = frameToFloat(frame, settings)
		assertContentEquals(result, image.pixelValues)
	}
	
	@Test
	fun getAscii() {
		val ascii: List<List<Char>> = image.getAscii()
		val charSet = image.settings.charSet.getChars()
		
		for ((i, row) in ascii.withIndex())
			for ((j, char) in row.withIndex())
				assert(char == (if (i == j) charSet.last() else charSet.first()))
	}
	
	@Test
	fun flipHorizontal() {
		image.flipHorizontal()
		
		for (i in pixels.indices) {
			for (j in pixels[i].indices) {
				val expected: Float = if (j == pixels[i].size - 1 - i) 1f else 0f
				assert(image.pixelValues[i][j] == expected)
			}
		}
	}
	
	@Test
	fun scaleDown() {
		pixels = MutableList(4) { y ->
			MutableList(4) { x ->
				if ((x + y) % 2 == 0) 1f else 0f
			}
		}
		
		val newWidth = 2
		val newHeight = 2
		
		image = Image(pixels, settings)
		image.scaleDown(newWidth, newHeight)
		
		for (x in 0 until newWidth)
			for (y in 0 until newHeight)
				assert(image.pixelValues[y][x] == .5f)
	}
	
	@Test
	fun detectEdges() {
		val height = 10
		val width = 10
		
		pixels = MutableList(height) {
			MutableList(width) { x ->
				if (x < width / 2) 0f else 1f
			}
		}
		
		image = Image(pixels, settings)
		image.detectEdges()
		
		for (x in 0 until height)
			for (y in 0 until width)
				assert(image.pixelValues[x][y] == if (y == 4) 1f else 0f)
	}
}
