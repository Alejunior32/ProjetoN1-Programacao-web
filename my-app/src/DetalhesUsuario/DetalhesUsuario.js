import React, { useState, useEffect } from 'react';
import './DetalhesUsuario.css';
import { Link, useParams } from 'react-router-dom';

const DetalhesUsuario = ({ token }) => {
  const [usuario, setUsuario] = useState([]);
  const {idUsuario} = useParams();

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const response = await fetch(`http://localhost:8080/v1/usuarios/${idUsuario}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUsuario(data);
        } else {
          console.error('Erro ao buscar etiquetas:', response.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchUsuarios();
  }, [token, idUsuario]);

  return (
    <div className="etiquetas-container">
      <div className="quadrado">
        <h2>Detalhes do Usuário</h2>
        <table className="etiquetas-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>Etiquetas</th>
            </tr>
          </thead>
          <tbody>
    {usuario && (
        <tr key={usuario.id} className="etiqueta-item">
            <td>{usuario.id}</td>
            <td>{usuario.email}</td>
            {usuario.etiquetas && usuario.etiquetas.length > 0 ? (
        usuario.etiquetas.map((etiqueta) => (
            <tr key={etiqueta.id} className="etiqueta-item">
                <td>{etiqueta.id}</td>
                <td>{etiqueta.nomeEtiqueta}</td>
            </tr>
        ))
    ) : (
        <tr>
            <td colSpan="3">Não há etiquetas</td>
        </tr>
    )}
        </tr>
    )}
</tbody>
        </table>
        <Link to="/admin/usuarios" className="btn btn-success">
              Voltar
          </Link>
        </div>
      </div>
  );
};

export default DetalhesUsuario;