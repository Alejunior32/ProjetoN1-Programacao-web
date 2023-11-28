import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './ListaAdmin.css';

const ListaAdmin = ({ token }) => {
  const [admins, setAdmins] = useState([]);
  const [adminExcluido, setAdminExcluido] = useState(null);

  useEffect(() => {
    const fetchAdmins = async () => {
      try {
        const response = await fetch('http://localhost:8080/v1/admins', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          const data = await response.json();
          setAdmins(data.content);
        } else {
          console.error('Erro ao buscar admins:', response.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchAdmins();
  }, [token, adminExcluido]);

  const handleExcluirAdmin = async (adminId) => {
    try {
      const response = await fetch(`http://localhost:8080/v1/usuarios/${adminId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.ok) {
        setAdminExcluido(adminId);
      } else {
        console.error('Erro ao excluir admin:', response.status);
      }
    } catch (error) {
      console.error('Erro durante a chamada da API:', error);
    }
  };

  return (
    <div className="etiquetas-container">
      <div className="quadrado">
        <h2>Lista de Admins</h2>
        <div className="etiquetas-header">
          <Link to="/admin/admins/novoAdmin" className="btn btn-primary">Criar Novo Admin</Link>
        </div>
        <table className="etiquetas-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>Detalhes</th>
              <th>Editar</th>
              <th>Deletar</th>
            </tr>
          </thead>
          <tbody>
            {admins.map((admin) => (
              <tr key={admin.id} className="etiqueta-item">
                <td>{admin.id}</td>
                <td>{admin.email}</td>
                <td><Link to={`/admin/admins/detalhes/${admin.id}`} className='btn btn-primary'>Detalhes</Link></td>
                <td><Link to={`/admin/admins/editar/${admin.id}`} className='btn btn-warning'>Editar</Link></td>
                <td>
                  <button
                    className='btn btn-danger'
                    onClick={() => handleExcluirAdmin(admin.id)}>
                    Excluir
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <Link to={`/admin/usuarios`} className='btn btn-dark'>Lista Usuários</Link>
      </div>
    </div>
  );
};

export default ListaAdmin;