using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Data.Persistence.Migrations
{
    public partial class Initial2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_ProductStores",
                table: "ProductStores");

            migrationBuilder.RenameColumn(
                name: "ProductId",
                table: "Ingredients",
                newName: "RecipeId");

            migrationBuilder.AddColumn<double>(
                name: "Cost",
                table: "Recipes",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddColumn<int>(
                name: "InstructionNr",
                table: "Instructions",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<Guid>(
                name: "ProductStoreId",
                table: "Ingredients",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AddPrimaryKey(
                name: "PK_ProductStores",
                table: "ProductStores",
                column: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_ProductStores",
                table: "ProductStores");

            migrationBuilder.DropColumn(
                name: "Cost",
                table: "Recipes");

            migrationBuilder.DropColumn(
                name: "InstructionNr",
                table: "Instructions");

            migrationBuilder.DropColumn(
                name: "ProductStoreId",
                table: "Ingredients");

            migrationBuilder.RenameColumn(
                name: "RecipeId",
                table: "Ingredients",
                newName: "ProductId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_ProductStores",
                table: "ProductStores",
                columns: new[] { "ProductId", "StoreId" });
        }
    }
}
