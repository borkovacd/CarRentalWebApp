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
  gearNumber: string;
  driveType: string;
  numberOfSeats: number;
  numberOfDoors: number;
  rentalPrice: number;
  name: string;
  description: string;
}
