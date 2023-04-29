function searchConferenceRooms() {
    let startDate = document.getElementById('start-date').value;
    let endDate = document.getElementById('end-date').value;
    const capacity = document.getElementById('capacity').value;


    startDate =

    axios.post('http://localhost:8080/conference-rooms/search', { startDate, endDate, capacity })
        .then(response => {
            const conferenceRooms = response.data;
            const availableConferenceRooms = document.getElementById('available-conference-rooms');
            availableConferenceRooms.innerHTML = '';
            conferenceRooms.forEach(conferenceRoom => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${conferenceRoom.name}</td>
                    <td>${conferenceRoom.capacity} persons</td>
                `;
                availableConferenceRooms.appendChild(tr);
            });
        })
        .catch(error => {
            console.log(error);
            alert('Error occurred while searching for available conference rooms.');
        });
}




