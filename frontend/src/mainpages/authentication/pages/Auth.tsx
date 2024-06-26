import { useEffect, useState } from "react";
import SetTitle from "../../../components/common/SetTitle";
import logo from "../../../assets/logo.png";
import { AuthResponse } from "../service/AuthenticationService";
import { SignUpService } from "../service/SignUpService";
import { LoginService } from "../service/LoginService";
import SuccessPopup from "../../../components/common/PopUs/SuccessPopup";
import { useNavigate } from "react-router-dom";

function Auth() {
  const [authType, setAuthType] = useState<string>("login");
  const [message, setMessage] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [error, setError] = useState<any>(<></>);
  const [popup, setPopup] = useState<any>(<></>);
  const [processStatus, setProcessStatus] = useState<boolean>(false);
  const [popupMessage, setPopupMessage] = useState<string>("");
  const navigate = useNavigate();
  const setType = (val: string) => {
    if (val === "login") setAuthType("signup");
    else setAuthType("login");
  };

  const submitButtonTrigger = async () => {
    if (authType == "signup") {
      const signupResponse: AuthResponse = await SignUpService(
        email,
        password,
        confirmPassword
      );
      console.log(signupResponse);
      if (!signupResponse.isStatus) setMessage(signupResponse.message);
      else {
        setMessage("");
        setProcessStatus(true);
        setPopupMessage(signupResponse.message);
        setAuthType("login");
      }
    }
    if (authType == "login") {
      const loginResponse: AuthResponse = await LoginService(email, password);
      if (!loginResponse.isStatus) setMessage(loginResponse.message);
      else {
        setMessage("");
        navigate("/dashBoard", { replace: true });
      }
    }
  };

  useEffect(() => {
    setError(<span className="text-red-300">{message}</span>);
  }, [message]);

  useEffect(() => {
    if (processStatus)
      setPopup(
        <SuccessPopup
          title="Account Created Successfully"
          message={popupMessage}
          path="/"
          butonName="Okay"
        />
      );
  }, [processStatus]);

  return (
    <>
      {<SetTitle title={authType} />}
      {popup}
      <div className="flex min-h-screen flex-col justify-center py-12 sm:px-6 lg:px-8 bg-primary">
        <div className="sm:mx-auto sm:w-full sm:max-w-md">
          <img
            className="mx-auto h-32 min-w-32 bg-amber-700 rounded-full"
            src={logo}
            alt="Your Company"
          />
          <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-third">
            {authType === "login"
              ? "Sign in to your account"
              : "Sign up to create account"}
          </h2>
          <p className="mt-2 text-center text-sm text-third">
            Or{" "}
            <a
              onClick={() => {
                setType(authType);
              }}
              className="font-medium text-fourth hover:text-[#af9f33] cursor-pointer"
            >
              {authType === "login" ? " Create Account ?" : " login ?"}
            </a>
          </p>
        </div>

        <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
          <div className="bg-secondary py-8 px-4 sm:shadow sm:rounded-lg sm:px-10">
            <form className="space-y-6">
              <div>
                {error}
                <label
                  htmlFor="email"
                  className="block text-sm font-medium text-third"
                >
                  Email address
                </label>
                <div className="mt-1">
                  <input
                    onChange={(e) => {
                      setEmail(e.target.value);
                    }}
                    id="email"
                    name="email"
                    type="email"
                    autoComplete="email"
                    required
                    className="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                  />
                </div>
              </div>

              <div>
                <label
                  htmlFor="password"
                  className="block text-sm font-medium text-third"
                >
                  Password
                </label>
                <div className="mt-1">
                  <input
                    onChange={(e) => {
                      setPassword(e.target.value);
                    }}
                    id="password"
                    name="password"
                    type="password"
                    autoComplete="current-password"
                    required
                    className="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                  />
                </div>

                {authType === "signup" && (
                  <>
                    <div className="mt-6">
                      <label
                        htmlFor="confirm_password"
                        className="block text-sm font-medium text-third"
                      >
                        Confirm Password
                      </label>
                      <div className="mt-1">
                        <input
                          onChange={(e) => {
                            setConfirmPassword(e.target.value);
                          }}
                          id="confirm_password"
                          name="password"
                          type="password"
                          autoComplete="current-password"
                          required
                          className="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                        />
                      </div>
                    </div>
                  </>
                )}
              </div>

              {authType === "login" && (
                <>
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <input
                        id="remember-me"
                        name="remember-me"
                        type="checkbox"
                        className="h-4 w-4 rounded border-gray-300 text-secondary focus:ring-[#ffe642]"
                      />
                      <label
                        htmlFor="remember-me"
                        className="ml-2 block text-sm text-third"
                      >
                        Remember me
                      </label>
                    </div>

                    <div className="text-sm">
                      <a
                        href="#"
                        className="font-medium text-fourth hover:text-[#af9f33]"
                      >
                        Forgot your password?
                      </a>
                    </div>
                  </div>
                </>
              )}
              <div>
                <button
                  onClick={() => submitButtonTrigger()}
                  type="button"
                  className="flex w-full justify-center rounded-md border transition-colors duration-150 border-transparent bg-fourth py-2 px-4 text-sm font-medium text-third shadow-sm hover:bg-[#af9f33] focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                >
                  {authType === "login" ? "Sign in" : "Sign up"}
                </button>
              </div>
            </form>

            <div className="mt-6">
              <div className="relative">
                <div className="absolute inset-0 flex items-center">
                  <div className="w-full border-t border-gray-300" />
                </div>
                <div className="relative flex justify-center text-sm">
                  <span className="bg-secondary px-2 text-third">
                    Or continue with
                  </span>
                </div>
              </div>

              <div className="mt-6 grid grid-cols-3 gap-3">
                <div>
                  <a
                    href="#"
                    className="inline-flex w-full justify-center rounded-md border border-gray-300 bg-secondary hover:bg-[#af9f33] py-2 px-4 text-sm font-medium text-fourth  shadow-sm duration-150 transition-colors"
                  >
                    <span className="sr-only">Sign in with Facebook</span>
                    <svg
                      className="h-5 w-5"
                      aria-hidden="true"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path
                        fillRule="evenodd"
                        d="M20 10c0-5.523-4.477-10-10-10S0 4.477 0 10c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V10h2.54V7.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V10h2.773l-.443 2.89h-2.33v6.988C16.343 19.128 20 14.991 20 10z"
                        clipRule="evenodd"
                      />
                    </svg>
                  </a>
                </div>

                <div>
                  <a
                    href="#"
                    className="inline-flex w-full justify-center rounded-md border border-gray-300 bg-secondary hover:bg-[#af9f33] py-2 px-4 text-sm font-medium text-fourth  shadow-sm duration-150 transition-colors"
                  >
                    <span className="sr-only">Sign in with Twitter</span>
                    <svg
                      className="h-5 w-5"
                      aria-hidden="true"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path d="M6.29 18.251c7.547 0 11.675-6.253 11.675-11.675 0-.178 0-.355-.012-.53A8.348 8.348 0 0020 3.92a8.19 8.19 0 01-2.357.646 4.118 4.118 0 001.804-2.27 8.224 8.224 0 01-2.605.996 4.107 4.107 0 00-6.993 3.743 11.65 11.65 0 01-8.457-4.287 4.106 4.106 0 001.27 5.477A4.073 4.073 0 01.8 7.713v.052a4.105 4.105 0 003.292 4.022 4.095 4.095 0 01-1.853.07 4.108 4.108 0 003.834 2.85A8.233 8.233 0 010 16.407a11.616 11.616 0 006.29 1.84" />
                    </svg>
                  </a>
                </div>

                <div>
                  <a
                    href="#"
                    className="inline-flex w-full justify-center rounded-md border border-gray-300 bg-secondary hover:bg-[#af9f33] py-2 px-4 text-sm font-medium text-fourth  shadow-sm duration-150 transition-colors"
                  >
                    <span className="sr-only">Sign in with GitHub</span>
                    <svg
                      className="h-5 w-5"
                      aria-hidden="true"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path
                        fillRule="evenodd"
                        d="M10 0C4.477 0 0 4.484 0 10.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0110 4.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.203 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.942.359.31.678.921.678 1.856 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0020 10.017C20 4.484 15.522 0 10 0z"
                        clipRule="evenodd"
                      />
                    </svg>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Auth;
