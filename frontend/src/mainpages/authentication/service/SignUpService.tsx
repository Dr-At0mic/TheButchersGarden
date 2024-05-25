import { AuthResponse, AuthenticationService } from "./AuthenticationService";
import { Response } from "../../../systemutils/models/Models";
import axios from "axios";

interface signupRequest {
  emailAddress: string;
  password: string;
}

export async function SignUpService(
  email: string,
  password: string,
  confirmPassword: string
): Promise<AuthResponse> {
  const validationResponse: AuthResponse = AuthenticationService(
    "signup",
    email,
    password,
    confirmPassword
  );
  if (!validationResponse.isStatus) return validationResponse;

  const body: signupRequest = {
    emailAddress: email,
    password: password,
  };
  try {
    const serverResponse = await axios.post(
      import.meta.env.VITE_GATEWAY_DOMAIN + "auth/register",
      body
    );
    const response: Response = serverResponse.data;
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
