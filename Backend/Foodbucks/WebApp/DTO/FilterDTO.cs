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
        [Required]
        public List<string> IncludedProducts { get; set; }
        [Required]
        public List<string> IncludingBrands { get; set; }
        [Required]
        public List<string> OnlyStores { get; set; }
    }
}
