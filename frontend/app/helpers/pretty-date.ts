import { helper } from '@ember/component/helper';

export function prettyDate(params: [string]) {
  let date = new Date(params[0]);
  let repr = "";
  if (date.getDate() < 10) {
    repr += `0${date.getDate()}`;
  } else {
    repr += date.getDate();
  }
  if ((date.getMonth() + 1) < 10) {
    repr += `.0${date.getMonth() + 1}`;
  } else {
    repr += `.${date.getMonth() + 1}`;
  }
  repr += `.${date.getFullYear()}`;
  return repr;
}

export default helper(prettyDate);
