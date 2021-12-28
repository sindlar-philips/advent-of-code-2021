import day01.SonarSweep
import day02.Dive
import day03.BinaryDiagnostic
import day04.GiantSquid
import day05.HydrothermalVenture
import day06.Lanternfish
import day07.TheTreacheryOfWhales
import day08.SevenSegmentSearch
import day09.SmokeBasin
import day10.SyntaxScoring
import day11.DumboOctopus
import day12.PassagePathing
import day13.TransparentOrigami
import day14.ExtendedPolymerization
import day15.Chiton
import day16.PacketDecoder
import day17.TrickShot
import day18.Snailfish
import day19.BeaconScanner
import day20.TrenchMap
import day21.DiracDice
import day22.ReactorReboot
import day23.Amphipods
import day24.ArithmeticLogicUnit
import day25.SeaCucumber
import java.io.File

fun main() = listOf(
    SonarSweep, Dive, BinaryDiagnostic, GiantSquid, HydrothermalVenture, Lanternfish, TheTreacheryOfWhales, SevenSegmentSearch, SmokeBasin, SyntaxScoring,
    DumboOctopus, PassagePathing, TransparentOrigami, ExtendedPolymerization, Chiton, PacketDecoder, TrickShot, Snailfish, BeaconScanner, TrenchMap,
    DiracDice, ReactorReboot, Amphipods, ArithmeticLogicUnit, SeaCucumber
).forEach { it.run() }

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}
