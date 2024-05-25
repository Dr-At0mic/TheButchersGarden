import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function AccountVerification() {
  const navigate = useNavigate();
  const [text, setText] = useState<string>("Processing....");
  const { token } = useParams();
  const [disable, setDisable] = useState(false);
  useEffect(() => {
    console.log("verfication");
    if (!disable) {
      const headers = {
        "Content-Type": "application/json; charset=utf-8",
      };
      axios
        .get(
          import.meta.env.VITE_GATEWAY_DOMAIN +
            "auth/accountActivation?token=" +
            token,
          {
            headers: headers,
            withCredentials: true,
          }
        )
        .then((response) => {
          if (response.data.status) {
            navigate("/");
            setText("Verified");
          }
        })
        .catch(() => {
          setText("failed");
        });
    }
    setDisable(true);
  }, []);
  return (
    <>
      <div className="w-[100%] h-[100dvh] bg-secondary flex justify-center items-center">
        <div className="">
          <span className="text-white text-4xl font-semibold">{text}</span>
        </div>
      </div>
    </>
  );
}

export default AccountVerification;
