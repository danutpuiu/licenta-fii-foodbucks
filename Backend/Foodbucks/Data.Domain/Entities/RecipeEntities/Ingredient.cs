using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Domain.Entities.RecipeEntities
{
    public class Ingredient
    {
        private Ingredient()
        {
        }

        public Guid Id { get; private set; }
        public Guid ProductId { get; private set; }
        public string Name { get; private set; }
        public double Quantity { get; private set; }
        public string UnitOfMeasurement { get; private set; }
        public double Cost { get; private set; }

        public static Ingredient Create(Guid productId, string name, double quantity, double cost, string unitOfMeasurement)
        {
            var instance = new Ingredient { Id = Guid.NewGuid() };
            instance.Update(productId, name, quantity, cost, unitOfMeasurement);
            return instance;
        }

        public void Update(Guid productId, string name, double quantity, double cost, string unitOfMeasurement)
        {
            ProductId = productId;
            Name = name;
            Quantity = quantity;
            Cost = cost;
            UnitOfMeasurement = unitOfMeasurement;
        }
    }
}
