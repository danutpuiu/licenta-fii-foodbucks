using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class ProductDTO
    {
        [Required]
        public String UnitOfMeasurement { get; set; }
        [Required]
        public String Name { get; set; }
        [Required]
        public String Brand { get; set; }
        [Required]
        public double Quantity { get; set; }
        public List<PriceInfoDTO> Prices { get; set; }
    }
}
