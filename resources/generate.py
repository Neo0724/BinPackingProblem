import random

def generate():
    random_type = [
        "Cloth", "Handphone", "Laptop", "Book", "Tablet", "Shoes", "Camera",
        "Kitchenware", "Toiletries", "Toy", "Speaker", "Monitor", "Printer",
        "Pet Food", "Food Container", "Cleaning Supplies", "Small Furniture",
        "Power Bank", "Fitness Equipment", "Stationery", "Medical Supplies"
    ]
    
    with open("random_truck.txt", "w") as f:
        for truck_id in range(1, 101):
            # Option 1: use integer weight
            random_weight_int = random.randint(5, 90)
            # Option 2: use double weight
            random_weight_double = round(random.uniform(5.0, 90.0), 2)
            random_quantity = random.randint(1, 5)
            parcel_type = random.choice(random_type)

            line = f"{truck_id},{random_weight_int},{parcel_type},{random_quantity}"
            # line = f"{truck_id},{random_weight_double},{parcel_type},{random_quantity}"

            f.write(line + "\n")

generate()
