    package dev.boooiil.historia.core.scoreboard

    import net.kyori.adventure.text.Component
    import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
    import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
    import org.bukkit.Bukkit
    import org.bukkit.scoreboard.Criteria
    import org.bukkit.scoreboard.DisplaySlot
    import org.bukkit.scoreboard.Objective
    import org.bukkit.scoreboard.Scoreboard
    import java.util.*

    /**
     * Scoreboard utility to manipulate player scoreboards.
     */
    class ScoreboardBuilder {

        /** Scoreboard associated with this adapter.  */
        private val scoreboard: Scoreboard

        /** Objective associated with this adapter.  */
        private val objective: Objective

        private var lines: MutableList<Component> = mutableListOf()

        init {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard()

            objective = scoreboard.registerNewObjective(
                UUID.randomUUID().toString(),
                Criteria.create(UUID.randomUUID().toString()),
                Component.empty(),
            )
            objective.displaySlot = DisplaySlot.SIDEBAR
        }

        /**
         * Create a header for your scoreboard.
         *
         * HEADER <--
         *
         * LINE
         *
         * ...
         *
         * @param header - the header to set.
         */
        fun header(header: Component): ScoreboardBuilder {
            objective.displayName(header)
            return this
        }

        /**
         * Add a line for your scoreboard.
         *
         * HEADER
         *
         * LINE <--
         *
         * ...
         *
         * @param line - the line to add.
         */
        fun addLine(line: Component): ScoreboardBuilder {
            lines.add(line)
            return this
        }

        /**
         * Build the scoreboard.
         */
        fun build(): Scoreboard {
            var i = 15
            for (line in lines) {
                if (i <= 0) throw IllegalStateException("Cannot build scoreboard with more than 15 lines")

                val str = LegacyComponentSerializer.legacySection().serialize(line)
                objective.getScore(str).score = i
                i--
            }
            return scoreboard
        }
    }
