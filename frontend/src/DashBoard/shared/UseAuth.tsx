import Cookies from "js-cookie";

export const UseAuth = () => {
  const getAccessToken = (): string | undefined => {
    return Cookies.get("AccessToken");
  };
  return getAccessToken()?.toString();
};
