using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;

namespace Data.Domain.Entities.OperationEntities
{
    public class RecipeFilter
    {
        public double Cost { get; set; }
        public String Name { get; set; }
        public RecipeRatingType Rating { get; set; }
        public int Votes { get; set; }
        public int CookingTime { get; set; }
        public int Calories { get; set; }

        public List<Product> IncludingIngreditents { get; set; }
        public List<Product> IncludingBrands { get; set; }
        public List<Store> IncludingStores { get; set; }

        public RecipeFilter()
        {
            IncludingIngreditents = new List<Product>();
            IncludingBrands = new List<Product>();
            IncludingStores = new List<Store>();

            Cost = 0;
            Name = "";
            Rating = 0;
            Votes = 0;
            CookingTime = 0;
            Calories = 0;
        }
    }
}
