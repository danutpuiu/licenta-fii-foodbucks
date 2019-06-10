using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class StoreDTO
    {
        [Required, MaxLength(100000)]
        public String Name { get; set; }
        public String Description { get; set; }
        public String Website { get; set; }
        public List<ProductDTO> Products { get; set; }
    }
}
