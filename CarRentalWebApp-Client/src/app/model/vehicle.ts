export interface Vehicle {
  id: number;
  typeName: string;
  brandName: string;
  model: string;
  bodyTypeName: string;
  fuelTypes?: string[];
  engineVolume: number;
  enginePower: number;
  transmissionType: string;
  gearNumber: number;
  driveType: string;
  numberOfSeats: number;
  numberOfDoors: number;
  rentalPrice: number;
  name: string;
  description: string;
  imageName: string;
}
