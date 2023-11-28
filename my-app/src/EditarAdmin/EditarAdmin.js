import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import './EditarAdmin.css';

const EditarAdmin = ({ token }) => {
  const {idAdmin} = useParams();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [editadoComSucesso, setEditadoComSucesso] = useState(false);

  useEffect(() => {
    const fetchAdmin = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/admins/${idAdmin}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        const admin = response.data;
        setEmail(admin.email);
      } catch (error) {
        console.error('Erro ao buscar admin:', error);
      }
    };

    fetchAdmin();
  }, [idAdmin, token]);

  const handleEditarAdmin = async () => {
    try {
      await axios.put(
        `http://localhost:8080/v1/admins/${idAdmin}`,
        { email, senha },
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setEditadoComSucesso(true);
    } catch (error) {
      console.error('Erro ao editar admin:', error);
    }
  };

  return (
    <div className="container">
      {editadoComSucesso ? (
        <div className="newQuadrado">
          <h2>Admin editado com sucesso!</h2>
          <Link to="/admin/admins" className="link">
            Voltar para a lista de admins
          </Link>
        </div>
      ) : (
        <div className="form-wrapper">
          <h2>Editar Admin</h2>
          <label htmlFor="email">Novo Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label htmlFor="senha">Nova Senha:</label>
          <input
            type="password"
            id="senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
          <div className="button-container">
            <button className='btn btn-warning' onClick={handleEditarAdmin}>
              Salvar Edições
            </button>
          </div>
          <Link to="/admin/admins" className="link">
            Cancelar
          </Link>
        </div>
      )}
    </div>
  );
  }

export default EditarAdmin;