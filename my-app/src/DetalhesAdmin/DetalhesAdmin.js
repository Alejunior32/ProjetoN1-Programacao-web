import React, { useState, useEffect } from 'react';
import './DetalhesAdmin.css';
import { Link, useParams } from 'react-router-dom';

const DetalhesAdmin = ({ token }) => {
  const [admin, setAdmin] = useState([]);
  const {idAdmin} = useParams();

  useEffect(() => {
    const fetchAdmins = async () => {
      try {
        const response = await fetch(`http://localhost:8080/v1/admins/${idAdmin}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          const data = await response.json();
          setAdmin(data);
        } else {
          console.error('Erro ao buscar admin:', response.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchAdmins();
  }, [token, idAdmin]);

  return (
    <div className="etiquetas-container">
      <div className="quadrado">
        <h2>Detalhes do Admin</h2>
        <table className="etiquetas-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
    {admin && (
        <tr key={admin.id} className="etiqueta-item">
            <td>{admin.id}</td>
            <td>{admin.email}</td>
        </tr>
    )}
</tbody>
        </table>
        <Link to="/admin/admins" className="btn btn-success">
              Voltar
          </Link>
        </div>
      </div>
  );
};

export default DetalhesAdmin;