import axios from "axios";
import { AuthResponse, AuthenticationService } from "./AuthenticationService";
import { Response } from "../../../systemutils/models/Models";

interface loginRequest {
  emailAddress: string;
  password: string;
}

export async function LoginService(
  email: string,
  password: string
): Promise<AuthResponse> {
  const validationResponse: AuthResponse = AuthenticationService(
    "login",
    email,
    password,
    ""
  );
  if (!validationResponse.isStatus) return validationResponse;

  const body: loginRequest = {
    emailAddress: email,
    password: password,
  };
  const headers = {
    "Content-Type": "application/json; charset=utf-8",
  };
  try {
    const serverResponse = await axios.post(
      import.meta.env.VITE_GATEWAY_DOMAIN + "auth/login",
      body,
      { headers, withCredentials: true }
    );
    const response: Response = serverResponse.data;
    console.log(response);
    if (response.status)
      return {
        isStatus: true,
        message: response.message,
      };
    return {
      isStatus: false,
      message: response.message,
    };
  } catch (error) {
    return {
      isStatus: false,
      message: "server unavailable",
    };
  }
}
