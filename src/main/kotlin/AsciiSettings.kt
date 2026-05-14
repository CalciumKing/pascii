data class AsciiSettings(
	val isInverted: Boolean = false,
	val isMirrored: Boolean = true,
	val onlyEdges: Boolean = false,
	val noise: Boolean = false,
	val charSet: CharSet = CharSet.ASCII_MINIMAL,
	val resX: Int,
	val resY: Int
)
