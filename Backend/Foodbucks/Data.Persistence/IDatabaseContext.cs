using Microsoft.EntityFrameworkCore;
using Data.Domain.Entities.RecipeEntities;

namespace Data.Persistence
{
    public interface IDatabaseContext
    {
        DbSet<Ingredient> Ingredients { get; set; }
        DbSet<Instruction> Instructions { get; set; }
        DbSet<Product> Products { get; set; }
        DbSet<ProductStore> ProductStores { get; set; }
        DbSet<Recipe> Recipes { get; set; }
        DbSet<Store> Stores { get; set; }
    }
}
