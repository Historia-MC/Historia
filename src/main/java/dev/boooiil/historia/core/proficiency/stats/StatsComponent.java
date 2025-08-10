package dev.boooiil.historia.core.proficiency.stats;

import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.util.JSONSerializable;

public interface StatsComponent extends JSONSerializable {

    void advance(StatsType type);

    void decrease(StatsType type);

    int getLevel(StatsType type);

    double getExperience(StatsType type);

    void setLevel(StatsType type, int level);

    void setExperience(StatsType type, double experience);

}
