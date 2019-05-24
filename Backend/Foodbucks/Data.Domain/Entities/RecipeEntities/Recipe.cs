using System;

namespace Data.Domain.Entities.RecipeEntities
{
    public enum RecipeRatingType : byte
    {
        NotRated,
        Bad,
        Good,
        Great
    }
    public class Recipe
    {
        private Recipe()
        {

        }

        public Guid Id { get; private set; }
        public string Name { get; private set; }
        public string Description { get; private set; }
        public int Servings { get; private set; }
        public int Calories { get; private set; }
        public int CookingTime { get; private set; }
        public int Likes { get; private set; }
        public int Votes { get; private set; }
        public RecipeRatingType Rating { get; private set; }

        public static Recipe Create(string name, string description, int servings, int calories,
            int cookingTime, int likes, int votes, RecipeRatingType rating)
        {
            var instance = new Recipe { Id = Guid.NewGuid() };
            instance.Update(name, description, servings, calories, cookingTime, likes, votes, rating);
            return instance;
        }

        public void Update(string name, string description, int servings, int calories,
            int cookingTime, int likes, int votes, RecipeRatingType rating)
        {
            Name = name;
            Description = description;
            Servings = servings;
            Calories = calories;
            CookingTime = cookingTime;
            Likes = likes;
            Votes = votes;
            Rating = rating;
        }

    }
}
