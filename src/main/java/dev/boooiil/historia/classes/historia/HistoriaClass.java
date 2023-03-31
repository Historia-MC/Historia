package dev.boooiil.historia.classes.historia;

import dev.boooiil.historia.configuration.ClassConfig;

/**
 * Hold player information specific to historia users.
 */
public class HistoriaClass {

      private String className;

      private float baseHealth;
      private float maxHealth;

      private float baseExperienceGain;
      private float baseExperienceMod;

      private ClassConfig classConfig;

      public HistoriaClass(String className) {

            this.classConfig = ClassConfig.getConfig(className); 

            this.baseExperienceGain = classConfig.getBaseExperienceGain();
            this.baseExperienceMod = 1.68f;

            this.baseHealth = classConfig.getBaseHealth();
            this.maxHealth = classConfig.getMaxHealth();
                  
      }

      /**
       * This function returns the class name of the object
       * 
       * @return The class name.
       */
      public String getClassName() {

            return this.className;

      }

      /**
       * This function returns the base health of the player
       * 
       * @return The baseHealth variable is being returned.
       */
      public float getBaseHealth() {

            return this.baseHealth;

      }

      /**
       * It returns the maxHealth variable
       * 
       * @return The maxHealth variable is being returned.
       */
      public float getMaxHealth() {

            return this.maxHealth;

      }

      /**
       * This function returns the base experience gain of the player
       * 
       * @return The baseExperienceGain variable is being returned.
       */
      public float getBaseExperienceGain() {

            return this.baseExperienceGain;
            
      }

      /**
       * This function returns the base experience modifier
       * 
       * @return The baseExperienceMod
       */
      public float getBaseExperienceMod() {

            return this.baseExperienceMod;
                  
      }

      /**
       * This function returns the classConfig object
       * 
       * @return The classConfig object.
       */
      public ClassConfig getClassConfig() {

            return this.classConfig;

      }

      /**
       * The function takes in a level and returns the modified health of the player based on the
       * level.
       * 
       * @param level The level of the Pokemon
       * @return The modified health of the character.
       */
      public float calcModifiedHealth(int level) {

            return (this.baseHealth + ((this.maxHealth - this.baseHealth) / 100)) * level;

      }
}
