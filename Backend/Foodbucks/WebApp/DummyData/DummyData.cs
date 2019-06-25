using System;
using System.Collections.Generic;
using System.Linq;
using Data.Domain.Entities.RecipeEntities;
using Data.Persistence;
using Logic;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;

namespace WebApp.DummyData
{
    public class DummyData
    {
        public static void Initialize (IApplicationBuilder app)
        {
            using (var serviceScope = app.ApplicationServices.GetService<IServiceScopeFactory>().CreateScope())
            {
                var context = serviceScope.ServiceProvider.GetService<DatabaseContext>();

                if (context.Stores != null && context.Stores.Any())
                    return;
                List<Store> stores = new List<Store>
                {
                    Store.Create("KMart", "KMart is the best store.", "https://www.KMart.com"),
                    Store.Create("Kepco", "Kepco is the best store.", "https://www.Kepco.com"),
                    Store.Create("Shop Ro 2000", "Shop Ro 2000 is the best store.", "https://www.shopro2000.com"),
                    Store.Create("GoShop", "GoShop is the best store.", "https://www.GoShop.com")
                };
                context.Stores.AddRange(stores);
                context.SaveChanges();

                List<Product> products = new List<Product>
                {
                    Product.Create("sugar", "SugarBrand", 1, "kilogram", 1),
                    Product.Create("flour", "FlourBrand", 1, "kilogram", 1),
                    Product.Create("water", "WaterBrand", 2.5, "litre", 2),
                    Product.Create("soy sauce", "Soy B", 500, "millilitre", 2),
                    Product.Create("chicken breast", "Kf Chicken", 1, "kilogram", 1),
                    Product.Create("salt", "SaltBrand", 1, "kilogram", 1)
                };
                context.Products.AddRange(products);
                context.SaveChanges();

                List<ProductStore> productStores = new List<ProductStore>
                {
                    ProductStore.Create(products[0].Id, stores[0].Id, 2.99),
                    ProductStore.Create(products[0].Id, stores[1].Id, 3.09),
                    ProductStore.Create(products[0].Id, stores[2].Id, 2.98),
                    ProductStore.Create(products[0].Id, stores[3].Id, 2.99),

                    ProductStore.Create(products[1].Id, stores[0].Id, 2.47),
                    ProductStore.Create(products[1].Id, stores[1].Id, 2.50),
                    ProductStore.Create(products[1].Id, stores[2].Id, 2.99),
                    ProductStore.Create(products[1].Id, stores[3].Id, 2.01),

                    ProductStore.Create(products[2].Id, stores[0].Id, 2.59),
                    ProductStore.Create(products[2].Id, stores[1].Id, 3.59),
                    ProductStore.Create(products[2].Id, stores[2].Id, 4.89),
                    ProductStore.Create(products[2].Id, stores[3].Id, 3.09),

                    ProductStore.Create(products[3].Id, stores[0].Id, 2.22),
                    ProductStore.Create(products[3].Id, stores[1].Id, 2.23),
                    ProductStore.Create(products[3].Id, stores[2].Id, 2.24),
                    ProductStore.Create(products[3].Id, stores[3].Id, 2.03),


                    ProductStore.Create(products[4].Id, stores[0].Id, 4.41),
                    ProductStore.Create(products[4].Id, stores[1].Id, 5.49),
                    ProductStore.Create(products[4].Id, stores[2].Id, 3.99),
                    ProductStore.Create(products[4].Id, stores[3].Id, 4.79),

                    ProductStore.Create(products[5].Id, stores[0].Id, 1.41),
                    ProductStore.Create(products[5].Id, stores[1].Id, 1.49),
                    ProductStore.Create(products[5].Id, stores[2].Id, 1.99),
                    ProductStore.Create(products[5].Id, stores[3].Id, 1.79),
                };
                context.ProductStores.AddRange(productStores);
                context.SaveChanges();

                Recipe recipe = Recipe.Create(
                    "Pancakes",
                    "New day, you want to start it right. Try this fresh and tasty Pancakes recipe!",
                    4,
                    500,
                    30,
                    0,
                    0,
                    0,
                    0);
                context.Recipes.Add(recipe);
                context.SaveChanges();

                List<Ingredient> ingredients = new List<Ingredient>
                {
                    Ingredient.Create(recipe.Id, productStores[2].Id, "sugar", 100, 2.98, "grams", 1),
                    Ingredient.Create(recipe.Id, productStores[7].Id, "flour", 500, 2.01, "grams", 1),
                    Ingredient.Create(recipe.Id, productStores[11].Id, "water", 100, 1.09, "millilitre", 1)
                };
                context.Ingredients.AddRange(ingredients);
                context.SaveChanges();

                List<Instruction> instructions = new List<Instruction>
                {
                    Instruction.Create(recipe.Id, "In a large bowl, sift together the flour, baking powder, " +
                    "salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; " +
                    "mix until smooth.", 1),
                    Instruction.Create(recipe.Id, "Heat a lightly oiled griddle or frying pan over medium high " +
                    "heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. " +
                    "Brown on both sides and serve hot.", 2)
                };
                context.Instructions.AddRange(instructions);
                context.SaveChanges();
                
            }
        }

    }
}
