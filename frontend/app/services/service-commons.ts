export const AMOUNT_PAGE = 10;

export interface IEntityCreatedResponse {
  id: string;
  message: string;
}

export interface IListDTO<T> {
  content: Array<T>
}

export interface IPageDTO<T> extends IListDTO<T> {
  page: number;
  amount: number;
  total: number;
}

export function fetchAjax(promiseParameters: JQuery.AjaxSettings): Promise<any> {
  return new Promise((resolve, reject) => {
    $.ajax(promiseParameters)
      .then(resolve)
      .catch(reject);
  });
}