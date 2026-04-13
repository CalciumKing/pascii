@Suppress("unused")
enum class CharSet(val charString: String) {
	ASCII_MINIMAL(".,-~:;!#$@"),
	ASCII_DETAILED(".`^\\\",:;Il!i~+_-?[]{}1()|/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"),
	BLOCKY(" ░▒▓█"),
	BRAIL(" ⠁⠃⠇⠏⠟⠿⡿⣿"),
	BOXES(" ─│┌┐└┘├┤┬┴┼ "),
	SHAPES(" .·˚°○●■□▲▼◆◇"),
	VOID(" . ");
	
	fun getChars(): CharArray = charString.toCharArray()
}
