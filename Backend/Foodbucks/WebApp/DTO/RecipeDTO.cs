using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class RecipeDTO
    {
        [Required, MaxLength(100000)]
        public String Name { get; set; }
        public String Description { get; set; }
        public List<IngredientDTO> Ingredients { get; set; }
        public List<InstructionStepDTO> InstructionSteps { get; set; }
        public int Servings { get; set; }
        public int Calories { get; set; }
        public int CookingTime { get; set; }
        public int Likes { get; set; }
        public int Votes { get; set; }
        public double Cost { get; set; }
        public double Rating { get; set; }
    }
}
