using Data.Domain.Entities.RecipeEntities;
using Microsoft.EntityFrameworkCore;

namespace Data.Persistence
{
    public sealed class DatabaseContext : DbContext, IDatabaseContext
    {
        public DatabaseContext(DbContextOptions<DatabaseContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        public DatabaseContext()
        {
            Database.EnsureCreated();
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<ProductStore>()
                .HasKey(pairItem => new { pairItem.ProductId, pairItem.StoreId });
        }

        public DbSet<Ingredient> Ingredients { get; set; }
        public DbSet<Instruction> Instructions { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<ProductStore> ProductStores { get; set; }
        public DbSet<Recipe> Recipes { get; set; }
        public DbSet<Store> Stores { get; set; }
    }
}
