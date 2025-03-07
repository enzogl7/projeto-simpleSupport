$(document).ready(function () {
    paginacaoTabela('tabelaFuncionarios')
});

document.getElementById('pesquisaTelefoneUsuario').addEventListener('input', function (e) {
    let telefone = e.target.value.replace(/\D/g, '');

    if (telefone.length > 10) {
        telefone = telefone.replace(/^(\d{2})(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else {
        telefone = telefone.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, '($1) $2-$3');
    }

    e.target.value = telefone;
});

document.getElementById("pesquisaEmailUsuario").addEventListener("keyup", filtrarTabela);
document.getElementById("pesquisaNomeUsuario").addEventListener("keyup", filtrarTabela);
document.getElementById("pesquisaTelefoneUsuario").addEventListener("keyup", filtrarTabela);

function modalConvidarFuncionario() {
    $('#modalConvidarFuncionario').modal('show');
}

function convidarFuncionario() {
    var emailFuncionario = document.getElementById('emailFuncionario').value;

    Swal.fire({
        title: "Enviando convite...",
        text: "Aguarde enquanto enviamos o email.",
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    $.ajax({
        url: '/empresa/convidarfuncionario',
        type: 'POST',
        data: {
            emailFuncionario: emailFuncionario
        },
        complete: function (xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Funcionário convidado com sucesso. Peça para que o mesmo verifique sua caixa de entrada.",
                        icon: "success",
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    break;
                case 404:
                    Swal.fire({
                        title: "Ops!",
                        text: "Não existe um usuário com esse email.",
                        icon: "warning",
                        confirmButtonText: "OK"
                    })
                    break;
                case 400:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao convidar este funcionário.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}

function removerDaEmpresa(button) {
    var idFuncionario = button.getAttribute('data-id');
    Swal.fire({
        title: 'Tem certeza que deseja remover este funcionário da empresa?',
        text: "Caso cometa um erro, terá que convidar esse usuário a empresa novamente.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, remover.',
        cancelButtonText: 'Não, voltar.',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            removerFuncionario(idFuncionario);
        } else {
            Swal.fire('Cancelado', 'O funcionário não foi removido', 'info');
        }
    });
}

function removerFuncionario(idFuncionario) {
    $.ajax({
        url: '/empresa/removerfuncionario',
        type: 'POST',
        data: {
            idFuncionario: idFuncionario
        },
        success: function (response) {
            Swal.fire({
                title: "Pronto!",
                text: "Funcionário removido com sucesso.",
                icon: "success",
                confirmButtonText: 'OK'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                title: "Ops!",
                text: "Erro ao remover funcionário",
                icon: "error",
                confirmButtonText: "OK"
            });
        }
    });
}


function filtrarTabela() {
    var emailFilter = document.getElementById("pesquisaEmailUsuario").value.toLowerCase();
    var nomeFilter = document.getElementById("pesquisaNomeUsuario").value.toLowerCase();
    var telefoneFilter = document.getElementById("pesquisaTelefoneUsuario").value.toLowerCase();

    var table = document.querySelector(".table");
    var rows = table.querySelectorAll("tbody tr");

    rows.forEach(function(row) {
        var email = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
        var nome = row.querySelector("td:nth-child(3)").textContent.toLowerCase();
        var telefone = row.querySelector("td:nth-child(4)").textContent.toLowerCase();

        var emailMatch = email.indexOf(emailFilter) > -1 || emailFilter === "";
        var nomeMatch = nome.indexOf(nomeFilter) > -1 || nomeFilter === "";
        var telefoneMatch = telefone.indexOf(telefoneFilter) > -1 || telefoneFilter === "";


        if (emailMatch && nomeMatch && telefoneMatch) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}