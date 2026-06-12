import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertContentEquals

class CharSetTest {
	@ParameterizedTest
	@EnumSource(CharSet::class)
	fun getChars(enum: CharSet) {
		assertContentEquals(enum.charString.toCharArray(), enum.getChars())
	}
}
