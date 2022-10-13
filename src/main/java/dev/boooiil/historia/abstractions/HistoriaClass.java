package dev.boooiil.historia.abstractions;

import dev.boooiil.historia.configuration.ClassConfig;

public class HistoriaClass {

      private String className;

      private float baseHealth;
      private float maxHealth;

      private float baseExperienceGain;
      private float baseExperienceMod;

      private ClassConfig classConfig;

      public HistoriaClass(String className) {

            this.classConfig = ClassConfig.getConfig(className); 

            this.baseExperienceGain = classConfig.baseExperienceGain;
            this.baseExperienceMod = 1.68f;

            this.baseHealth = classConfig.baseHealth;
            this.maxHealth = classConfig.maxHealth;
                  
      }

      public String getClassName() {

            return this.className;

      }

      public float getBaseHealth() {

            return this.baseHealth;

      }

      public float getMaxHealth() {

            return this.maxHealth;

      }

      public float getBaseExperienceGain() {

            return this.baseExperienceGain;
            
      }

      public float getBaseExperienceMod() {

            return this.baseExperienceMod;
                  
      }

      public ClassConfig getClassConfig() {

            return this.classConfig;

      }

      public float calcModifiedHealth(int level) {

            return (this.baseHealth + ((this.maxHealth - this.baseHealth) / 100)) * level;

      }
}
