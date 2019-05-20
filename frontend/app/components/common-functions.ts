export function amountPages(total: number, amount: number) {
  let result = Math.ceil(total / amount);
  if (!result) {
    return 1;
  }
  return result;
}