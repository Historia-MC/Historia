package dev.boooiil.historia.classes.historia.proficiency;

import dev.boooiil.historia.configuration.ClassConfig;

/**
 * Hold player information specific to historia users.
 */
public class HistoriaProficiency extends BaseProficiency {

      private String className;

      private float baseHealth;
      private float maxHealth;

      private float baseExperienceGain;
      private float baseExperienceMod;

      private BaseProficiency baseClass;

      public HistoriaProficiency(String className) {

            super(className);

            this.baseClass = ClassConfig.getConfig(className); 

            this.baseExperienceGain = baseClass.getBaseExperienceGain();
            this.baseExperienceMod = 1.68f;

            this.baseHealth = baseClass.getBaseHealth();
            this.maxHealth = baseClass.getMaxHealth();
                  
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
       * @return The BaseClass object.
       */
      public BaseProficiency getClassConfig() {

            return this.baseClass;

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
