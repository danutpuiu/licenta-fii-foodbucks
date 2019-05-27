using Data.Domain.Entities.RecipeEntities;
using System.Collections.Generic;

namespace WebApp.DTO
{
    public class HomepageDTO
    {
        public IEnumerable<Recipe> Recipes { get; set; }
    }
}
