  // Create interactive stars
        const starsContainer = document.querySelector('.stars');
        const numberOfStars = 100;
        let mouseX = 0;
        let mouseY = 0;

        // Create stars
        for (let i = 0; i < numberOfStars; i++) {
            const star = document.createElement('div');
            star.className = 'star';
            
            // Random star properties
            const size = Math.random() * 3;
            const x = Math.random() * window.innerWidth;
            const y = Math.random() * window.innerHeight;
            
            star.style.width = `${size}px`;
            star.style.height = `${size}px`;
            star.style.left = `${x}px`;
            star.style.top = `${y}px`;
            star.style.opacity = Math.random();
            
            starsContainer.appendChild(star);
        }

        // Track mouse movement
        document.addEventListener('mousemove', (e) => {
            mouseX = e.clientX;
            mouseY = e.clientY;
            
            const stars = document.querySelectorAll('.star');
            stars.forEach(star => {
                const rect = star.getBoundingClientRect();
                const starX = rect.left + rect.width / 2;
                const starY = rect.top + rect.height / 2;
                
                // Calculate distance from mouse
                const deltaX = mouseX - starX;
                const deltaY = mouseY - starY;
                const distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                
                // Move stars away from mouse
                if (distance < 100) {
                    const angle = Math.atan2(deltaY, deltaX);
                    const push = (100 - distance) / 10;
                    
                    const newX = starX - Math.cos(angle) * push;
                    const newY = starY - Math.sin(angle) * push;
                    
                    star.style.left = `${newX}px`;
                    star.style.top = `${newY}px`;
                    star.style.opacity = '1';
                }
            });
        });

        // Slowly return stars to original positions
        setInterval(() => {
            const stars = document.querySelectorAll('.star');
            stars.forEach(star => {
                const currentX = parseFloat(star.style.left);
                const currentY = parseFloat(star.style.top);
                const originalX = parseFloat(star.dataset.originalX || star.style.left);
                const originalY = parseFloat(star.dataset.originalY || star.style.top);
                
                if (!star.dataset.originalX) {
                    star.dataset.originalX = star.style.left;
                    star.dataset.originalY = star.style.top;
                }
                
                star.style.left = `${currentX + (originalX - currentX) * 0.05}px`;
                star.style.top = `${currentY + (originalY - currentY) * 0.05}px`;
                star.style.opacity = parseFloat(star.style.opacity) * 0.95 + 0.05;
            });
        }, 50);