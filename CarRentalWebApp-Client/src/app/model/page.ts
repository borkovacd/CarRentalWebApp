export interface Page<T> {
  currentPage: number;
  itemsPerPage: number;
  totalItems: number;
  items: T[];
}
