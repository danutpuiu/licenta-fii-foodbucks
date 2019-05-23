using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Domain.Entities.RecipeEntities
{
    public class Product
    {
        private Product()
        {

        }

        public Guid Id { get; private set; }
        public string Name { get; private set; }
        public string Brand { get; private set; }
        public double Quantity { get; private set; }
        public string UnitOfMeasurement { get; private set; }

        public static Product Create(string name, string brand, double quantity, string unitOfMeasurement)
        {
            var instance = new Product { Id = Guid.NewGuid() };
            instance.Update(name, brand, quantity, unitOfMeasurement);
            return instance;
        }

        public void Update(string name, string brand, double quantity, string unitOfMeasurement)
        {
            Name = name;
            Brand = brand;
            Quantity = quantity;
            UnitOfMeasurement = unitOfMeasurement;
        }
    }
}
