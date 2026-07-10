import { useEffect, useState } from "react";
import http from "../api/http";

export default function useIceCreamShops() {
  const [iceCreamShops, setIceCreamShops] = useState([]);
  const [sLoading, setLoading] = useState(true);
  const [sError, setError] = useState("");

  // ucitavanje svih sladoledzinica
  async function fetchAll() {
    setLoading(true);

    try {
      const res = await http.get("/iceCreamShop");
      const data = Array.isArray(res.data) ? res.data : [];

      setIceCreamShops(
        data
          .filter((x) => x && x.id != null)
          .map((x) => ({
            id: x.id,
            name: x.name ?? "",
            address: x.address ?? "",
          }))
      );

      setError("");
    } catch (e) {
      setError(e?.response?.data?.message || e.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchAll();
  }, []);

  return {
    iceCreamShops,
    sLoading,
    sError,
    reload: fetchAll,
  };
}