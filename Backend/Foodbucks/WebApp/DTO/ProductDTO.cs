using System;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class ProductDTO
    {
        [Required, MaxLength(100000)]
        public String UnitOfMeasurement { get; set; }
        [Required, MaxLength(100000)]
        public String Name { get; set; }
        [Required, MaxLength(100000)]
        public String Brand { get; set; }
        [Required, MaxLength(100000)]
        public String Store { get; set; }
        public double Quantity { get; set; }
        public double Price { get; set; }
    }
}
