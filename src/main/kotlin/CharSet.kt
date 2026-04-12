@Suppress("unused")
enum class CharSet(val chars: CharArray) {
	ASCII_MINIMAL(".,-~:;!#$@".toCharArray()),
	ASCII_DETAILED(".`^\\\",:;Il!i~+_-?[]{}1()|/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$".toCharArray()),
	BLOCKY(" ░▒▓█".toCharArray()),
	BRAIL(" ⠁⠃⠇⠏⠟⠿⡿⣿".toCharArray()),
	BOXES(" ─│┌┐└┘├┤┬┴┼ ".toCharArray()),
	SHAPES(" .·˚°○●■□▲▼◆◇".toCharArray()),
	VOID(" . ".toCharArray()),
}
