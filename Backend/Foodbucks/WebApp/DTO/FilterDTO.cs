using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class FilterDTO
    {
        public string Name { get; set; }
        public double Cost { get; set; }
        public double Rating { get; set; }
        public int VotesNumber { get; set; }
        public int CookingTime { get; set; }
        public string IncludedProducts { get; set; }
        public string IncludingBrands { get; set; }
        public string OnlyStores { get; set; }
    }
}
