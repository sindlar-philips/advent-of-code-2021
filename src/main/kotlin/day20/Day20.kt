package day20

import Coordinate
import PuzzleData

data class Image(val pixels: Map<Coordinate, Char>) {

    private val minX = pixels.minOf { it.key.x }
    private val maxX = pixels.maxOf { it.key.x }
    private val minY = pixels.minOf { it.key.y }
    private val maxY = pixels.maxOf { it.key.y }

    fun enlarge(extraPixels: Int): Image {

        val enlarged = (minX - extraPixels..maxX + extraPixels).flatMap { x ->
            (minY - extraPixels..maxY + extraPixels).map { y ->
                val pixel = Coordinate(x, y)
                pixel to (pixels[pixel] ?: '0')
            }
        }.toMap()
        return Image(enlarged)
    }

    fun enhance(): Image {
        val enhanced = (minX + 1 until maxX).flatMap { x ->
            (minY + 1 until maxY).map { y ->
                val pixel = Coordinate(x, y)
                val newValue = getPixelValue(pixel)
                pixel to Day20.algorithm[newValue]
            }
        }.toMap()
        return Image(enhanced)
    }

    internal fun getPixelValue(pixel: Coordinate): Int {
        val bitString = listOf(
            pixels[Coordinate(pixel.x - 1, pixel.y - 1)]!!,
            pixels[Coordinate(pixel.x, pixel.y - 1)]!!,
            pixels[Coordinate(pixel.x + 1, pixel.y - 1)]!!,
            pixels[Coordinate(pixel.x - 1, pixel.y)]!!,
            pixels[pixel]!!,
            pixels[Coordinate(pixel.x + 1, pixel.y)]!!,
            pixels[Coordinate(pixel.x - 1, pixel.y + 1)]!!,
            pixels[Coordinate(pixel.x, pixel.y + 1)]!!,
            pixels[Coordinate(pixel.x + 1, pixel.y + 1)]!!
        ).joinToString("")
        return bitString.toInt(2)
    }
}

object Day20 : Runnable {

    internal val algorithm = PuzzleData.load("/day20/day20-algorithm.txt") { parseAlgorithm(it) }
    internal val image = PuzzleData.load("/day20/day20-image.txt") { parseImage(it) }

    fun enhanceAndCountLitPixels(times: Int): Int {
        val enlarged = image.enlarge(times * 2)
        val enhanced = (1..times).fold(enlarged) { image, _ -> image.enhance() }
        return enhanced.pixels.count { it.value == '1' }
    }

    private fun pixelToBit(pixel: Char): Char =
        when (pixel) {
            '.' -> '0'
            '#' -> '1'
            else -> throw Exception("Unexpected character: $pixel")
        }

    private fun parseAlgorithm(lines: List<String>): String =
        lines.single().map { pixelToBit(it) }.joinToString("")

    private fun parseImage(lines: List<String>): Image {
        val pixels = lines.mapIndexed { y, pixels ->
            pixels.mapIndexed { x, pixel ->
                Coordinate(x, y) to pixelToBit(pixel)
            }
        }.flatten().toMap()
        return Image(pixels)
    }

    override fun run() {
        println("Day 20, 2x enhanced: ${enhanceAndCountLitPixels(2)}")
        println("Day 20, 50x enhanced: ${enhanceAndCountLitPixels(50)}")
    }
}